import { BrowserModule } from '@angular/platform-browser';
import { NgModule} from '@angular/core';
import { FormsModule} from '@angular/forms';
import { HttpClientModule, HttpClient, HttpInterceptor, HttpRequest, HttpHandler} from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';

import { AppService } from './app.service';
import { HomeComponent } from './home.component';
import { LoginComponent } from './login.component';
import { AppComponent } from './app.component';

import {HTTP_INTERCEPTORS} from '@angular/common/http';
import { Injectable } from '@angular/core';

export const routes: Routes = [
	{path: '', pathMatch: 'full', redirectTo: 'home'},
	{path: 'home', component: HomeComponent},
	{path: 'login', component: LoginComponent}	
];

@NgModule ({
	declarations: [
		AppComponent,
		HomeComponent,
		LoginComponent
	],
	imports: [
		RouterModule,
		RouterModule.forRoot(routes),
		BrowserModule,
		HttpClientModule,
		FormsModule
	],
	exports: [
		RouterModule
	],	
	providers: [AppService, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }],
	bootstrap: [AppComponent]
})

@Injectable({
  providedIn: 'root'
})
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}

export class AppModule{}
