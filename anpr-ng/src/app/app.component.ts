import { Component, ViewChild } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Observable, Subject } from 'rxjs';
import { ThemeService } from './core/services/theme.service';
import { MatDrawer } from '@angular/material/sidenav';
import { LoaderService } from './core/services/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ChatBot';
  isDarkTheme: Observable<boolean>;
  isDark = false;

  isLoggedIn = false;

  isLoading: Subject<boolean> = this.loaderService.isLoading;

  @ViewChild('drawer') drawer: MatDrawer;

  constructor(
    private authService: AuthService, 
    private themeService: ThemeService,
    private loaderService: LoaderService) { }

  ngOnChanges() {
    this.isLoading = this.loaderService.isLoading;
  }

  ngOnInit(): void {
    this.isDarkTheme = this.themeService.isDarkTheme;
    
    this.isLoggedIn = this.authService.isLoggedIn();
    
  }

  ngAfterViewInit() {
    
  }

  toggleDarkTheme(checked: boolean) {
    this.themeService.setDarkTheme(checked);
    this.isDark = checked;
  }

  logout() {
    this.authService.logout();
  }

  toggleSidenav() {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.drawer.toggle();
  }
}
