export class User {
  id: number = 0;
  email: string = '';
  password: string = '';
  firstName: string = '';
  lastName: string = '';
  phoneNumber: string = '';
  active: boolean = false;

  isValid(): boolean {
    if(this.firstName.length===0)
      return false;
    if(this.lastName.length===0)
      return false;
    if(this.email.length===0)
      return false;
    if(this.password.length===0)
      return false;
    if(this.phoneNumber.length===0)
      return false;
    return true;
  }
}
