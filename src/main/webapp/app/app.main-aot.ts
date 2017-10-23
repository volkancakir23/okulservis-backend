import { platformBrowser } from '@angular/platform-browser';
import { ProdConfig } from './blocks/config/prod.config';
import { OkulservisAppModuleNgFactory } from '../../../../target/aot/src/main/webapp/app/app.module.ngfactory';

ProdConfig();

platformBrowser().bootstrapModuleFactory(OkulservisAppModuleNgFactory)
.then((success) => console.log(`Application started`))
.catch((err) => console.error(err));
