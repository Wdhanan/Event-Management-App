import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar'; // Importez MatSnackBar
import { Router } from '@angular/router'; // Importez Router

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService,
    private snackBar: MatSnackBar, // for notification
    private router: Router 
  ) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      participantRole: ['NORMAL_PARTICIPANT', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.authService.signup(this.registerForm.value).subscribe({
        next: (response) => {
          console.log('Inscription Successfull', response);
          this.snackBar.open('Inscription Successfull !', 'close', {
            duration: 3000, // Durée en millisecondes
          });
          this.router.navigate(['/login']); // Redirection vers la page de connexion
        },
        error: (error) => {
          console.error('Inscription was not successfull :', error);
          this.snackBar.open('Inscription was not successfull.', 'Close', {
            duration: 3000,
          });
        }
      });
    }
  }

}
