import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  message: boolean;
  subscription: Subscription;


  constructor(public authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.subscription = this.authService.authenticated.subscribe(message => {
      this.message = message;
      if (this.message) {
        this.router.navigateByUrl('/projects');
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  login(): void {
    this.authService.login();
  }

  logout(): void {
    this.authService.logout();
  }
}
