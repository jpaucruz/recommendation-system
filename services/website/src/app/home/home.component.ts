import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductService } from '../service/product.service';
import { RecommendationService } from '../service/recommendation.service';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { WebactionsService } from '../service/webactions.service';
import { UserAction } from '../models/user-action.model';
import { interval, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { PurchaseService } from '../service/purchase.service';
import { UserPurchase } from '../models/user-purchase.model';

interface Product {
  id: number;
  name: string;
  imageUrl: string;
  description: string;
  quantity?: number;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'], // Corregido: estilo debe ser `styleUrls`
})
export class HomeComponent implements OnInit, OnDestroy {
  products: Product[] = [];
  cart: Product[] = [];
  recommendations: Product[] = [];
  isCartVisible = true; // Simplificación: no es necesario el tipo explícito aquí
  currentUser: string | null = null; // Inicialización más clara
  selectedProduct: Product | null = null;

  private recommendationsSubscription: Subscription | null = null;

  constructor(
    private productService: ProductService,
    private recommendationService: RecommendationService,
    private authService: AuthService,
    private router: Router,
    private webActionService: WebactionsService,
    private purchaseService: PurchaseService
  ) {}

  ngOnInit() {
    this.initializeUser();
    this.loadProducts();
    this.loadCart();
    this.setupRecommendations();
  }

  ngOnDestroy() {
    this.unsubscribeRecommendations();
  }

  /**
   * Inicializa el usuario actual.
   */
  private initializeUser(): void {
    this.currentUser = this.authService.getCurrentUser();
  }

  /**
   * Carga la lista de productos disponibles.
   */
  private loadProducts(): void {
    this.productService.getProducts().subscribe(
      (data) => {
        console.log('Productos cargados:', data);
        this.products = data;
      },
      (error) => {
        console.error('Error al cargar productos:', error);
      }
    );
  }

  /**
   * Carga el carrito desde el almacenamiento local.
   */
  private loadCart(): void {
    if (this.currentUser) {
      const savedCart = localStorage.getItem(`cart_${this.currentUser}`);
      if (savedCart) {
        this.cart = JSON.parse(savedCart);
      }
    }
  }

  /**
   * Configura la suscripción a las recomendaciones.
   */
  private setupRecommendations(): void {
    if (this.currentUser) {
      this.recommendationsSubscription = interval(15000)
        .pipe(
          switchMap(() => this.recommendationService.getRecommendations(this.currentUser!))
        )
        .subscribe(
          (data) => {
            console.log('Productos recomendados:', data);
            this.recommendations = data;
          },
          (error) => {
            console.error('Error al obtener recomendaciones:', error);
          }
        );
    }
  }

  /**
   * Cancela la suscripción a las recomendaciones.
   */
  private unsubscribeRecommendations(): void {
    this.recommendationsSubscription?.unsubscribe();
  }

  /**
   * Añade un producto al carrito.
   */
  addToCart(product: Product): void {
    this.trackUserAction('add-to-cart', product.id);

    const existingProduct = this.cart.find((item) => item.id === product.id);
    if (existingProduct) {
      existingProduct.quantity = (existingProduct.quantity || 0) + 1;
    } else {
      this.cart.push({ ...product, quantity: 1 });
    }

    this.saveCart();
  }

  /**
   * Alterna la visibilidad del carrito.
   */
  toggleCartVisibility(): void {
    this.isCartVisible = !this.isCartVisible;
  }

  /**
   * Devuelve el número total de productos en el carrito.
   */
  getCartCount(): number {
    return this.cart.reduce((total, product) => total + (product.quantity || 0), 0);
  }

  /**
   * Elimina un producto del carrito.
   */
  removeFromCart(product: Product): void {
    const index = this.cart.findIndex((item) => item.id === product.id);
    if (index !== -1) {
      const selectedProduct = this.cart[index];
      if ((selectedProduct.quantity || 0) > 1) {
        selectedProduct.quantity!--;
      } else {
        this.cart.splice(index, 1);
      }
      this.saveCart();
    }
  }

  /**
   * Finaliza la compra del carrito.
   */
  finalizePurchase(): void {

    if (!this.currentUser) return;

    const userPurchases: UserPurchase[] = [];
    this.cart.forEach(cartItem => {
      const userPurchase: UserPurchase = {
        amount: cartItem.quantity || 0,
        productId: cartItem.id.toString(),
        timestamp: new Date().toISOString().slice(0, 19) + 'Z',
      };
      userPurchases.push(userPurchase);
    });

    this.purchaseService.createPurchase(this.currentUser, userPurchases).subscribe(
      (data) => console.log('Compra registrada!:', data),
      (error) => console.error('Error al registrar la compra del usuario:', error)
    );

    this.cart.forEach((product) => {
      this.trackUserAction('purchase', product.id);
    });

    if (this.currentUser) {
      localStorage.removeItem(`cart_${this.currentUser}`);
    }

    this.cart = [];
  }

  /**
   * Abre un modal para mostrar los detalles del producto.
   */
  openProductModal(product: Product): void {
    this.selectedProduct = product;
  }

  /**
   * Maneja el cierre de sesión.
   */
  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  /**
   * Rastrea una acción del usuario.
   */
  private trackUserAction(action: string, productId: number): void {
    if (!this.currentUser) return;

    const userAction: UserAction = {
      action,
      productId: productId.toString(),
      timestamp: new Date().toISOString().slice(0, 19) + 'Z',
    };

    this.webActionService.sendUserAction(this.currentUser, userAction).subscribe(
      (data) => console.log('Acción del usuario registrada:', data),
      (error) => console.error('Error al registrar la acción del usuario:', error)
    );
  }

  /**
   * Guarda el carrito en el almacenamiento local.
   */
  private saveCart(): void {
    if (this.currentUser) {
      localStorage.setItem(`cart_${this.currentUser}`, JSON.stringify(this.cart));
    }
  }
}