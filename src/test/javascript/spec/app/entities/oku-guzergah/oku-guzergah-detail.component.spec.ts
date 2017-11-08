/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { OkulservisTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OkuGuzergahDetailComponent } from '../../../../../../main/webapp/app/entities/oku-guzergah/oku-guzergah-detail.component';
import { OkuGuzergahService } from '../../../../../../main/webapp/app/entities/oku-guzergah/oku-guzergah.service';
import { OkuGuzergah } from '../../../../../../main/webapp/app/entities/oku-guzergah/oku-guzergah.model';

describe('Component Tests', () => {

    describe('OkuGuzergah Management Detail Component', () => {
        let comp: OkuGuzergahDetailComponent;
        let fixture: ComponentFixture<OkuGuzergahDetailComponent>;
        let service: OkuGuzergahService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OkulservisTestModule],
                declarations: [OkuGuzergahDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OkuGuzergahService,
                    JhiEventManager
                ]
            }).overrideTemplate(OkuGuzergahDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OkuGuzergahDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OkuGuzergahService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OkuGuzergah(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.okuGuzergah).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
