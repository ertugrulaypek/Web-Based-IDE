import { TestBed } from '@angular/core/testing';

import { SourceFileContentService } from './source-file-content.service';

describe('SourceFileContentService', () => {
  let service: SourceFileContentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SourceFileContentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
