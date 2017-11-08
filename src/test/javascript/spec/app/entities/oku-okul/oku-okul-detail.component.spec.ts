/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { OkulservisTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OkuOkulDetailComponent } from '../../../../../../main/webapp/app/entities/oku-okul/oku-okul-detail.component';
import { OkuOkulService } from '../../../../../../main/webapp/app/entities/oku-okul/oku-okul.service';
import { OkuOkul } from '../../../../../../main/webapp/app/entities/oku-okul/oku-okul.model';

describe('Component Tests', () => {

    describe('OkuOkul Management Detail Component', () => {
        let comp: OkuOkulDetailComponent;
        let fixture: ComponentFixture<OkuOkulDetailComponent>;
        let service: OkuOkulService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OkulservisTestModule],
                declarations: [OkuOkulDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OkuOkulService,
                    JhiEventManager
                ]
            }).overrideTemplate(OkuOkulDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OkuOkulDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OkuOkulService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OkuOkul(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.okuOkul).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
