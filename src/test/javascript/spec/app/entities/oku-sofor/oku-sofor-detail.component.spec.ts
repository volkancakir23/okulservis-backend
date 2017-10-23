/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { OkulservisTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OkuSoforDetailComponent } from '../../../../../../main/webapp/app/entities/oku-sofor/oku-sofor-detail.component';
import { OkuSoforService } from '../../../../../../main/webapp/app/entities/oku-sofor/oku-sofor.service';
import { OkuSofor } from '../../../../../../main/webapp/app/entities/oku-sofor/oku-sofor.model';

describe('Component Tests', () => {

    describe('OkuSofor Management Detail Component', () => {
        let comp: OkuSoforDetailComponent;
        let fixture: ComponentFixture<OkuSoforDetailComponent>;
        let service: OkuSoforService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OkulservisTestModule],
                declarations: [OkuSoforDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OkuSoforService,
                    JhiEventManager
                ]
            }).overrideTemplate(OkuSoforDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OkuSoforDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OkuSoforService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OkuSofor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.okuSofor).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
