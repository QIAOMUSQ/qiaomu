package io.renren.modules.android.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.android.entity.Label;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelDao extends BaseMapper<Label> {
    Label findOne(int labelId);

    void save(Label label);





}
