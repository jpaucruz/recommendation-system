import { Component, Input, Output, EventEmitter } from '@angular/core';
import { UserAction } from '../models/user-action.model';
import { WebactionsService } from '../service/webactions.service';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-product-modal',
  templateUrl: './product-modal.component.html',
  styleUrl: './product-modal.component.css'
})
export class ProductModalComponent {

  @Input() product: any;
  @Output() close = new EventEmitter<void>();
  @Output() addToCartEvent = new EventEmitter<any>();
  currentUser: string | null = '';

  constructor(private webActionService: WebactionsService, private authService: AuthService) {}

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();
    const userAction: UserAction = {
      action: "view",
      productId: this.product.id,
      timestamp: "2024-11-19T19:51:45Z"
    };
    if (this.currentUser != null){
      this.webActionService.sendUserAction(this.currentUser,userAction).subscribe(
        (data) => {
          console.log("Response:", data);
        },
        (error) => {
          console.error("Error :", error);
        }
      );
    }
  }

  closeModal() {
    this.close.emit();
  }

  addToCart() {
    this.addToCartEvent.emit(this.product);
    // this.closeModal(); // Opcional: cerrar el modal después de añadir al carrito
  }

}
