/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { OkulservisTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OkuYolcuDetailComponent } from '../../../../../../main/webapp/app/entities/oku-yolcu/oku-yolcu-detail.component';
import { OkuYolcuService } from '../../../../../../main/webapp/app/entities/oku-yolcu/oku-yolcu.service';
import { OkuYolcu } from '../../../../../../main/webapp/app/entities/oku-yolcu/oku-yolcu.model';

describe('Component Tests', () => {

    describe('OkuYolcu Management Detail Component', () => {
        let comp: OkuYolcuDetailComponent;
        let fixture: ComponentFixture<OkuYolcuDetailComponent>;
        let service: OkuYolcuService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OkulservisTestModule],
                declarations: [OkuYolcuDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OkuYolcuService,
                    JhiEventManager
                ]
            }).overrideTemplate(OkuYolcuDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OkuYolcuDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OkuYolcuService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OkuYolcu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.okuYolcu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
