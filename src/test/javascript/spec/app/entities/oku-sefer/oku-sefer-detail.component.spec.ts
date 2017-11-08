/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { OkulservisTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OkuSeferDetailComponent } from '../../../../../../main/webapp/app/entities/oku-sefer/oku-sefer-detail.component';
import { OkuSeferService } from '../../../../../../main/webapp/app/entities/oku-sefer/oku-sefer.service';
import { OkuSefer } from '../../../../../../main/webapp/app/entities/oku-sefer/oku-sefer.model';

describe('Component Tests', () => {

    describe('OkuSefer Management Detail Component', () => {
        let comp: OkuSeferDetailComponent;
        let fixture: ComponentFixture<OkuSeferDetailComponent>;
        let service: OkuSeferService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OkulservisTestModule],
                declarations: [OkuSeferDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OkuSeferService,
                    JhiEventManager
                ]
            }).overrideTemplate(OkuSeferDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OkuSeferDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OkuSeferService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OkuSefer(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.okuSefer).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
