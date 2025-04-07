let cart = [];
let products = [];
let isCartVisible = false;

// Pomocnicza funkcja do wyświetlania powiadomień
function showNotification(message) {
    const notification = document.getElementById('notification');
    const messageElement = document.getElementById('notification-message');
    messageElement.textContent = message;
    notification.classList.add('show');
    setTimeout(() => {
        notification.classList.remove('show');
    }, 3000);
}

// Funkcja do formatowania ceny
function formatPrice(price) {
    return new Intl.NumberFormat('pl-PL', {
        style: 'currency',
        currency: 'PLN',
        minimumFractionDigits: 2
    }).format(price);
}

// Przełączanie widoku między produktami a koszykiem
function showProducts() {
    document.getElementById('products-container').classList.remove('hidden');
    document.getElementById('cart-container').classList.add('hidden');
    isCartVisible = false;
}

function showCart() {
    document.getElementById('products-container').classList.add('hidden');
    document.getElementById('cart-container').classList.remove('hidden');
    isCartVisible = true;
}

// Pobieranie produktów przy załadowaniu strony
async function loadProducts() {
    const loadingScreen = document.getElementById('loading-screen');
    try {
        loadingScreen.style.display = 'flex';
        const response = await fetch('http://localhost:8080/products');
        if (!response.ok) {
            throw new Error('Błąd podczas ładowania produktów');
        }
        products = await response.json();
        displayProducts();
    } catch (error) {
        console.error('Błąd podczas ładowania produktów:', error);
        showNotification('Nie udało się załadować produktów. Spróbuj ponownie później.');
    } finally {
        loadingScreen.style.display = 'none';
    }
}

// Dodawanie produktu do koszyka
function addToCart(productId) {
    const product = products.find(p => p.id === productId);
    if (!product) {
        showNotification('Nie znaleziono produktu.');
        return;
    }

    const cartItem = cart.find(item => item.id === productId);
    if (cartItem) {
        cartItem.quantity += 1;
    } else {
        cart.push({
            id: product.id,
            name: product.name,
            price: product.price,
            quantity: 1,
            stock: product.stock // Dodajemy stock do elementu koszyka
        });
    }

    updateCartDisplay();
    updateCartBadge();
    showNotification('Produkt został dodany do koszyka.');
}

// Aktualizacja badge'a koszyka
function updateCartBadge() {
    const cartCount = document.getElementById('cart-count');
    const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
    cartCount.textContent = totalItems;
}

// Usuwanie produktu z koszyka
function removeFromCart(productId) {
    const itemIndex = cart.findIndex(item => item.id === productId);
    if (itemIndex > -1) {
        cart.splice(itemIndex, 1);
        updateCartDisplay();
        updateCartBadge();
        showNotification('Produkt został usunięty z koszyka.');
    }
}

// Aktualizacja wyświetlania koszyka
function updateCartDisplay() {
    const cartItems = document.getElementById('cart-items');
    const cartTotal = document.getElementById('cart-total');

    cartItems.innerHTML = '';

    cart.forEach(item => {
        const cartItem = document.createElement('div');
        cartItem.className = 'cart-item';
        cartItem.innerHTML = `
            <div class="cart-item-info">
                <span class="cart-item-title">${item.name}</span>
                <span class="cart-item-quantity">Ilość: ${item.quantity}</span>
                <span class="cart-item-total">${formatPrice(item.price * item.quantity)}</span>
            </div>
            <button onclick="removeFromCart(${item.id})" class="remove-item">
                <i class="fas fa-trash"></i>
            </button>
        `;
        cartItems.appendChild(cartItem);
    });

    const total = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    cartTotal.textContent = formatPrice(total);
}

// Wyświetlanie produktów
function displayProducts() {
    const container = document.getElementById('products-container');
    container.innerHTML = '';  // Czyści kontener przed wyświetleniem nowych produktów

    products.forEach(product => {
        const productCard = document.createElement('div');
        productCard.className = 'product-card';
        productCard.innerHTML = `
    <div class="product-content">
        <img src="http://localhost:8080/images/produkt${product.id}.jpg" alt="Obrazek produktu">
        <h3 class="product-title">${product.name}</h3>
        <p class="product-description">${product.description}</p>
        <p class="product-price">${formatPrice(product.price)}</p>
        <button onclick="addToCart(${product.id})" class="add-to-cart">
            <i class="fas fa-shopping-cart"></i>
            Dodaj do koszyka
        </button>
    </div>
`;

        container.appendChild(productCard);
    });
}

// Składanie zamówienia
async function placeOrder() {
    if (cart.length === 0) {
        showNotification('Koszyk jest pusty.');
        return;
    }

    try {
        // Tworzenie zamówienia
        const orderId = await createOrder();

        // Dodawanie produktów do zamówienia i aktualizacja stanu magazynowego
        for (const item of cart) {
            await addOrderItem(orderId, item);
            await updateStock(item);
        }

        // Czyszczenie koszyka po złożeniu zamówienia
        cart = [];
        updateCartDisplay();
        updateCartBadge();
        showNotification('Zamówienie zostało złożone pomyślnie!');
        showProducts();
    } catch (error) {
        showNotification(`Nie udało się złożyć zamówienia: ${error.message}`);
    }
}

// Funkcja do tworzenia zamówienia
async function createOrder() {
    const orderResponse = await fetch('http://localhost:8080/orders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            users: { id: 1 } // Tymczasowo hardcodowany ID użytkownika
        })
    });

    if (!orderResponse.ok) {
        const errorMessage = await orderResponse.text();
        throw new Error(`Błąd podczas tworzenia zamówienia: ${errorMessage}`);
    }

    // Sprawdź czy Location header istnieje
    const locationHeader = orderResponse.headers.get('Location');
    if (!locationHeader) {
        // Jeśli brak Location header, spróbuj pobrać ID z ciała odpowiedzi
        try {
            const orderData = await orderResponse.json();
            return orderData.id; // Zakładając, że odpowiedź zawiera ID
        } catch (error) {
            throw new Error('Brak identyfikatora zamówienia w odpowiedzi');
        }
    }

    // Jeśli Location header istnieje, wyciągnij ID jak wcześniej
    const orderId = locationHeader.split('/').pop();
    return orderId;
}

// Funkcja do dodawania pozycji do zamówienia
async function addOrderItem(orderId, item) {
    const orderItemResponse = await fetch('http://localhost:8080/orderItems', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            orders: { id: orderId },
            products: { id: item.id },
            quantity: item.quantity
        })
    });
    if (!orderItemResponse.ok) {
        throw new Error('Błąd podczas dodawania produktu do zamówienia');
    }
}

// Funkcja do aktualizacji stanu magazynowego
async function updateStock(item) {
    const updateStockResponse = await fetch(`http://localhost:8080/products/${item.id}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            stock: item.stock - item.quantity
        })
    });
    if (!updateStockResponse.ok) {
        throw new Error('Błąd podczas aktualizacji stanu magazynowego');
    }
}

// Inicjalizacja przy załadowaniu strony
document.addEventListener('DOMContentLoaded', () => {
    loadProducts();
    showProducts(); // Domyślnie pokazuj produkty
});