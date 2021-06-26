import {AfterContentChecked, Component, OnInit} from '@angular/core';
import {Product} from '../../common/product';
import {SharedService} from '../../services/shared.service';
import {AuthService} from '../../services/auth.service';
import {FactureService} from '../../services/facture.service';
import {Facture} from '../../common/facture';
import {Router} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ModalLoginComponent} from '../modal-login/modal-login.component';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements AfterContentChecked {

  products: Product[] = [];

  constructor(
    private sharedService: SharedService,
    private authService: AuthService,
    private factureService: FactureService,
    private modalService: NgbModal,
    private router: Router
  ) { }

  ngAfterContentChecked() {
    this.products = this.sharedService.products;
  }

  deleteFormCard(id: string): void {
    this.sharedService.deleteProduct(id);
  }

  buyHandler(): void {
    if(this.authService.getUserId()!==null) {
      let facture = new Facture();
      facture.totalPrice = this.sharedService.priceVal;
      facture.totalProducts = this.sharedService.numberProducts;
      facture.date = new Date();
      this.factureService.save(facture,this.authService.getUserId()).subscribe(
        ()=> {
          this.sharedService.clear();
          this.router.navigate(['']);
        }, error => {
          console.log(error);
        }
      );
    } else {
      this.modalService.open(ModalLoginComponent, {centered: true});
    }
  }
}
