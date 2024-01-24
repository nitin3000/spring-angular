import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { AppService } from './app.service';

@Component({
  templateUrl: './home.component.html',
})
export class HomeComponent {
  title = 'Demo';
  greeting : any = {"id":"", "content":""};
  constructor(private app: AppService, private http: HttpClient){
  	if (this.authenticated())
  		http.get('resource').subscribe(data => this.greeting = data);
  }
  
  authenticated(){
    console.log(this.app.authenticated);
	return this.app.authenticated;
  }
}
