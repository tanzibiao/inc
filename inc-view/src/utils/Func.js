export default class Func {
  static currentUser() {
    const currentUser = window.localStorage.getItem('currentUser');
    return eval('(' + currentUser + ')');;
  }
}
