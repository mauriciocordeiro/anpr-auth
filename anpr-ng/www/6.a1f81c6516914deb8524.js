(window.webpackJsonp=window.webpackJsonp||[]).push([[6],{yIAU:function(t,n,o){"use strict";o.r(n),o.d(n,"AuthorizationModule",(function(){return A}));var e=o("Valr"),i=o("DUip"),c=o("UTcu"),a=o("TYT/"),r=o("AytR"),l=o("cUzu"),u=r.a.apiCheck,p=function(){function t(t){this.http=t}return t.prototype.check=function(t){var n=new FormData;return n.append("image",t),this.http.post(u+"/check",n)},t.\u0275fac=function(n){return new(n||t)(a.Yb(l.b))},t.\u0275prov=a.Kb({token:t,factory:t.\u0275fac,providedIn:"root"}),t}(),f=o("4xmj"),s=o("uLXW"),h=o("17Os"),b=o("p+mS"),m=o("GsDI"),d=o("2J1J");function v(t,n){1&t&&(a.Ub(0,"mat-icon",9),a.Cc(1,"block"),a.Tb())}function g(t,n){1&t&&(a.Ub(0,"mat-icon",10),a.Cc(1,"check_circle_outline"),a.Tb())}var w=[{path:"",component:function(){function t(t,n){this.authorizationService=t,this.snackBar=n,this.file=null}return t.prototype.ngOnInit=function(){},t.prototype.onChange=function(t){var n=this;this.vehicle=null,this.file=t.target.files[0];var o=new FileReader;o.onload=function(t){n.image=t.target.result},o.readAsDataURL(t.target.files[0]),this.onUpload()},t.prototype.onUpload=function(){var t=this;this.authorizationService.check(this.file).subscribe((function(n){t.vehicle=n,t.snackBar.success(n.allowed?"Allowed":"NOT Allowed",n.plate)}),(function(n){t.vehicle=null,console.log(n)}))},t.\u0275fac=function(n){return new(n||t)(a.Ob(p),a.Ob(f.a))},t.\u0275cmp=a.Ib({type:t,selectors:[["app-authorization"]],decls:11,vars:3,consts:[["fxLayoutAlign","center start",1,"main"],["fxFlex.xs","100","fxFlex","75","fxLayoutGap","10px"],["fxLayout","row","fxLayoutGap","10px","fxLayoutAlign","space-between center"],["type","file","capture","environment","accept","image/*",3,"change"],["mat-mini-fab","","color","primary",3,"click"],["fxLayout","row","fxLayout.xs","column","fxLayoutGap","10px","fxLayoutAlign","center center"],["alt","","fxFlex","",1,"img",3,"src"],["class","icon","color","warn","matTooltip","Not Allowed",4,"ngIf"],["class","icon","color","primary","matTooltip","Allowed",4,"ngIf"],["color","warn","matTooltip","Not Allowed",1,"icon"],["color","primary","matTooltip","Allowed",1,"icon"]],template:function(t,n){1&t&&(a.Ub(0,"div",0),a.Ub(1,"mat-card",1),a.Ub(2,"div",2),a.Ub(3,"input",3),a.cc("change",(function(t){return n.onChange(t)})),a.Tb(),a.Ub(4,"button",4),a.cc("click",(function(){return n.onUpload()})),a.Ub(5,"mat-icon"),a.Cc(6,"upload"),a.Tb(),a.Tb(),a.Tb(),a.Ub(7,"div",5),a.Pb(8,"img",6),a.Ac(9,v,2,0,"mat-icon",7),a.Ac(10,g,2,0,"mat-icon",8),a.Tb(),a.Tb(),a.Tb()),2&t&&(a.Cb(8),a.mc("src",n.image,a.vc),a.Cb(1),a.mc("ngIf",n.vehicle&&!n.vehicle.allowed),a.Cb(1),a.mc("ngIf",n.vehicle&&n.vehicle.allowed))},directives:[s.b,h.a,s.a,s.d,s.c,b.a,m.a,e.k,d.a],styles:[".main[_ngcontent-%COMP%]{position:relative;width:100vw;height:calc(100vh - 64px)}.img[_ngcontent-%COMP%]{padding-top:10px;max-width:600px}.icon[_ngcontent-%COMP%]{padding:10px;font-size:48px}"]}),t}(),canActivate:[c.a]}],x=function(){function t(){}return t.\u0275mod=a.Mb({type:t}),t.\u0275inj=a.Lb({factory:function(n){return new(n||t)},imports:[[i.f.forChild(w)],i.f]}),t}(),y=o("vvyD"),T=o("QJY3"),U=o("qiSS"),A=function(){function t(){}return t.\u0275mod=a.Mb({type:t}),t.\u0275inj=a.Lb({factory:function(n){return new(n||t)},imports:[[e.c,y.a,T.o,U.a,s.e,x]]}),t}()}}]);