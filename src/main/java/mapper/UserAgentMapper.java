package mapper;

import entity.UserAgent;
import entity.UserAgentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAgentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_agent
     *
     * @mbg.generated Fri Aug 25 22:36:36 CST 2017
     */
    long countByExample(UserAgentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_agent
     *
     * @mbg.generated Fri Aug 25 22:36:36 CST 2017
     */
    int deleteByExample(UserAgentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_agent
     *
     * @mbg.generated Fri Aug 25 22:36:36 CST 2017
     */
    int insert(UserAgent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_agent
     *
     * @mbg.generated Fri Aug 25 22:36:36 CST 2017
     */
    int insertSelective(UserAgent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_agent
     *
     * @mbg.generated Fri Aug 25 22:36:36 CST 2017
     */
    List<UserAgent> selectByExample(UserAgentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_agent
     *
     * @mbg.generated Fri Aug 25 22:36:36 CST 2017
     */
    int updateByExampleSelective(@Param("record") UserAgent record, @Param("example") UserAgentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_agent
     *
     * @mbg.generated Fri Aug 25 22:36:36 CST 2017
     */
    int updateByExample(@Param("record") UserAgent record, @Param("example") UserAgentExample example);
}