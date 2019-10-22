// from: https://github.com/angular/angular/issues/12409

import {TestBed, TestModuleMetadata} from '@angular/core/testing';
import {CUSTOM_ELEMENTS_SCHEMA, Type} from '@angular/core';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientModule} from '@angular/common/http';
import {_HttpClient, MenuService, ScrollService, SettingsService,} from '@delon/theme';
import {DelonAuthModule} from '@delon/auth';
import {SharedModule} from '@shared/shared.module';


import {DelonModule} from '../app/delon.module';

const resetTestingModule = TestBed.resetTestingModule,
  preventAngularFromResetting = () =>
    (TestBed.resetTestingModule = () => TestBed);
const allowAngularToReset = () =>
  (TestBed.resetTestingModule = resetTestingModule);

export const setUpTestBed = (moduleDef: TestModuleMetadata) => {
  beforeAll(done =>
    (async () => {
      resetTestingModule();
      preventAngularFromResetting();

      // region: schemas
      if (!moduleDef.schemas) {
        moduleDef.schemas = [];
      }
      moduleDef.schemas.push(CUSTOM_ELEMENTS_SCHEMA);
      // endregion

      // region: imports
      if (!moduleDef.imports) {
        moduleDef.imports = [];
      }
      moduleDef.imports.push(RouterTestingModule);
      moduleDef.imports.push(HttpClientModule);
      moduleDef.imports.push(DelonModule);
      moduleDef.imports.push(SharedModule);
      // auth
      moduleDef.imports.push(DelonAuthModule.forRoot());
      // endregion

      // region: providers
      if (!moduleDef.providers) {
        moduleDef.providers = [];
      }
      // load full services
      [SettingsService, MenuService, ScrollService, _HttpClient].forEach(
        (item: any) => {
          if (moduleDef.providers.includes(item)) {
            return;
          }
          moduleDef.providers.push(item);
        },
      );
      // endregion

      TestBed.configureTestingModule(moduleDef);
      await TestBed.compileComponents();

      // prevent Angular from resetting testing module
      TestBed.resetTestingModule = () => TestBed;
    })()
      .then(done)
      .catch(done.fail));

  afterAll(() => allowAngularToReset());
};

/**
 * get service instance
 */
export const getService = <T>(type: Type<T>): T => <T>TestBed.get(type);
