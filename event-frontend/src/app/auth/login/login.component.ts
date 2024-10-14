import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.username, this.password).subscribe(
      (response) => {
        
        
        
        console.log('Response:', response);

        if (response.role === 'ADMIN'){
          // Rediriger l'utilisateur
        this.router.navigate(['/dashboard/event-list']);

        }
        else if(response.role === 'NORMAL_PARTICIPANT'){

        }
        
      },
      (error) => {
        this.errorMessage = 'Échec de la connexion, veuillez vérifier vos informations.';
      }
    );
  }
}
