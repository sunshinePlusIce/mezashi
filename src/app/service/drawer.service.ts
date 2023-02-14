import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DrawerService {
  open: boolean = false;
  
  constructor() { }
  
  setDrawerState(v: boolean): boolean {
    this.open = v;
    console.log(this.open);
    return this.open;
  }

  getDrawerState(): boolean {
    return this.open;
  }
}
