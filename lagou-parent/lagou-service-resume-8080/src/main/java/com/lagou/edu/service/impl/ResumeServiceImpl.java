package com.lagou.edu.service.impl;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeDao resumeDao;//导入dao

    @Override
    public Resume findDefaultResumeByUserId(Long userId) {
        Resume resume = new Resume();
        resume.setUserId(userId);
        //1表示true，表示默认简历
        resume.setIsDefault(1);
        Example<Resume> eaample = Example.of(resume);
        return resumeDao.findOne(eaample).get();
    }
}
