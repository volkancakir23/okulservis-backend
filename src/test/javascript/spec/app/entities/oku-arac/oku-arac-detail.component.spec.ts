/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { OkulservisTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OkuAracDetailComponent } from '../../../../../../main/webapp/app/entities/oku-arac/oku-arac-detail.component';
import { OkuAracService } from '../../../../../../main/webapp/app/entities/oku-arac/oku-arac.service';
import { OkuArac } from '../../../../../../main/webapp/app/entities/oku-arac/oku-arac.model';

describe('Component Tests', () => {

    describe('OkuArac Management Detail Component', () => {
        let comp: OkuAracDetailComponent;
        let fixture: ComponentFixture<OkuAracDetailComponent>;
        let service: OkuAracService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OkulservisTestModule],
                declarations: [OkuAracDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OkuAracService,
                    JhiEventManager
                ]
            }).overrideTemplate(OkuAracDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OkuAracDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OkuAracService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OkuArac(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.okuArac).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
