import { Component } from '@angular/core';
import { DrawerService } from 'src/app/service/drawer.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent {
  constructor(
    public drawerService: DrawerService
  ) {

  }
}
