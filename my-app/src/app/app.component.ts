import { Component, OnInit } from '@angular/core';
import { ICreateOrderRequest, IPayPalConfig } from 'ngx-paypal';
import { OrdersService } from './Services/orders.service';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Route, Router, Routes } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { PopupErrorModelComponent } from './popup-error-model/popup-error-model.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  public payPalConfig ? : IPayPalConfig;
  showError: boolean | undefined;
  showCancel: boolean | undefined;
  showSuccess: boolean | undefined;
    constructor(private dialog:MatDialog,private ordersService:OrdersService,private http:HttpClient,private router:Router){}
    ngOnInit(): void {
      this.initConfig();
  }

  private async initConfig(): Promise<void> {
    const intent = "capture";
      this.payPalConfig = {
          //currency: 'USD',
          clientId: 'Aae_dlaEbcbqq35DtMaNCU1qdoP24tlXVnKbMTlCcpYiJnaapnAbRuAfCjM1dp7IacxPOv2GfH4Gz9Ir',
          onClick: (data) => {},
        style: { 
            shape: 'rect',
            color: 'gold',
            layout: 'vertical',
            label: 'paypal'
        },
        createOrderOnServer: async (data) =>{
          try{
            let response =  await fetch('http://localhost:8080/v1/create-order',
            {method:'POST', headers: { "Content-Type": "application/json; charset=utf-8" },
            body: JSON.stringify([{
                id:1,
                quantite:4,
                unit_amount:100
              },{
                id:2,
                quantite:4,
                unit_amount:22
              }])
            });
            let json_rep = await response.json();
            let orderid = json_rep.orderID
            return orderid
          }catch(err){
            console.log("deszq")
          }
        },
        onApprove: (data, actions)=> {
            console.log(data)
            this.http.post("http://localhost:8080/v1/complete-order",{
                "intent": "capture",
                "order_id": data.orderID
            }).subscribe({
                next: (data)=>{console.log(data)},
                error: (err)=>{console.log(err)}
            }) 
               
            actions.order.get().then((details:any)=>{
                console.log(details)
            })
        },

        onCancel: (data,actions)=> {
            console.log('OnCancel', data, actions);
            this.showCancel = true;
        },

        onError: (err)=> {
          this.dialog.open(PopupErrorModelComponent, {});
            console.log(err);
        }
      };
  }
}