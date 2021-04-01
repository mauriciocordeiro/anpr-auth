import { TestBed } from '@angular/core/testing';

import { TokenValidatorInterceptor } from './token-validator.interceptor';

describe('TokenValidatorInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      TokenValidatorInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: TokenValidatorInterceptor = TestBed.inject(TokenValidatorInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
