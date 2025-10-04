package in.tech_camp.protospace_sample.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace_sample.entity.CommentEntity;

@Mapper
public interface CommentRepository {
    @Select("SELECT c.*, u.id AS user_id, u.name AS user_name FROM comments c JOIN users u ON c.user_id = u.id WHERE c.prototype_id = #{prototypeId}")
    @Results(value = {
        @Result(property = "user.id", column = "user_id"), 
         @Result(property = "user.name", column = "user_name"),
        @Result(property = "prototype", column = "prototype_id", 
                one = @One(select = "in.tech_camp.protospace_sample.repository.PrototypeRepository.findById"))
    })
    List<CommentEntity> findByPrototypeId(Integer prototypeId);

  @Insert("INSERT INTO comments (content, user_id, prototype_id) VALUES (#{content}, #{user.id}, #{prototype.id})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(CommentEntity comment);
}
