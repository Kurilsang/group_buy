package site.kuril.domain.activity.service.trial.thread;

import site.kuril.domain.activity.adapter.repository.IActivityRepository;
import site.kuril.domain.activity.model.valobj.SkuVO;

import java.util.concurrent.Callable;

public class test implements Callable<SkuVO> {

    private final IActivityRepository activityRepository;

    public test(final IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public SkuVO call() throws Exception {
        activityRepository.test();
        return null;
    }
}
