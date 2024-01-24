import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import {HttpClient} from '@angular/common/http';
import { AppService } from './app.service';
import { finalize } from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  title = 'Demo';
  greeting : any;
  constructor(private app: AppService, private http: HttpClient, private router: Router){
  	this.app.authenticate(undefined, undefined);
  }
  
  logout(){
  	this.http.post('logout', {}).pipe(finalize (() => {
  		this.app.authenticated = false;
  		this.router.navigateByUrl('/');
  	})).subscribe();
  }
}
