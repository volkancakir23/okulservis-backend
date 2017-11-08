/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { OkulservisTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OkuVeliDetailComponent } from '../../../../../../main/webapp/app/entities/oku-veli/oku-veli-detail.component';
import { OkuVeliService } from '../../../../../../main/webapp/app/entities/oku-veli/oku-veli.service';
import { OkuVeli } from '../../../../../../main/webapp/app/entities/oku-veli/oku-veli.model';

describe('Component Tests', () => {

    describe('OkuVeli Management Detail Component', () => {
        let comp: OkuVeliDetailComponent;
        let fixture: ComponentFixture<OkuVeliDetailComponent>;
        let service: OkuVeliService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OkulservisTestModule],
                declarations: [OkuVeliDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OkuVeliService,
                    JhiEventManager
                ]
            }).overrideTemplate(OkuVeliDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OkuVeliDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OkuVeliService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OkuVeli(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.okuVeli).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
