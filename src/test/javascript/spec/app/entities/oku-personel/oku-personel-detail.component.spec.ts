/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { OkulservisTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OkuPersonelDetailComponent } from '../../../../../../main/webapp/app/entities/oku-personel/oku-personel-detail.component';
import { OkuPersonelService } from '../../../../../../main/webapp/app/entities/oku-personel/oku-personel.service';
import { OkuPersonel } from '../../../../../../main/webapp/app/entities/oku-personel/oku-personel.model';

describe('Component Tests', () => {

    describe('OkuPersonel Management Detail Component', () => {
        let comp: OkuPersonelDetailComponent;
        let fixture: ComponentFixture<OkuPersonelDetailComponent>;
        let service: OkuPersonelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OkulservisTestModule],
                declarations: [OkuPersonelDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OkuPersonelService,
                    JhiEventManager
                ]
            }).overrideTemplate(OkuPersonelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OkuPersonelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OkuPersonelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OkuPersonel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.okuPersonel).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
