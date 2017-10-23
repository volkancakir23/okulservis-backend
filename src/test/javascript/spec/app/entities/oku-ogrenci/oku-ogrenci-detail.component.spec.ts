/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { OkulservisTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OkuOgrenciDetailComponent } from '../../../../../../main/webapp/app/entities/oku-ogrenci/oku-ogrenci-detail.component';
import { OkuOgrenciService } from '../../../../../../main/webapp/app/entities/oku-ogrenci/oku-ogrenci.service';
import { OkuOgrenci } from '../../../../../../main/webapp/app/entities/oku-ogrenci/oku-ogrenci.model';

describe('Component Tests', () => {

    describe('OkuOgrenci Management Detail Component', () => {
        let comp: OkuOgrenciDetailComponent;
        let fixture: ComponentFixture<OkuOgrenciDetailComponent>;
        let service: OkuOgrenciService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OkulservisTestModule],
                declarations: [OkuOgrenciDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OkuOgrenciService,
                    JhiEventManager
                ]
            }).overrideTemplate(OkuOgrenciDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OkuOgrenciDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OkuOgrenciService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OkuOgrenci(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.okuOgrenci).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
