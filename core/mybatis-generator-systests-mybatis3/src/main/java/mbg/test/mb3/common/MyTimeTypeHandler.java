/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package mbg.test.mb3.common;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;

import mbg.test.common.MyTime;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author Jeff Butler
 * 
 */
public class MyTimeTypeHandler implements TypeHandler {

    /**
     * 
     */
    public MyTimeTypeHandler() {
        super();
    }

    public Object getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        MyTime answer = null;
        Time time = cs.getTime(columnIndex);
        if (time != null) {
            answer = new MyTime();

            Calendar c = Calendar.getInstance();
            c.setTime(time);

            answer.setHours(c.get(Calendar.HOUR_OF_DAY));
            answer.setMinutes(c.get(Calendar.MINUTE));
            answer.setSeconds(c.get(Calendar.SECOND));
        }
        
        return answer;
    }

    public Object getResult(ResultSet rs, String columnName)
            throws SQLException {
        MyTime answer = null;
        Time time = rs.getTime(columnName);
        if (time != null) {
            answer = new MyTime();

            Calendar c = Calendar.getInstance();
            c.setTime(time);

            answer.setHours(c.get(Calendar.HOUR_OF_DAY));
            answer.setMinutes(c.get(Calendar.MINUTE));
            answer.setSeconds(c.get(Calendar.SECOND));
        }
        
        return answer;
    }

    public void setParameter(PreparedStatement ps, int i, Object parameter,
            JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, jdbcType.TYPE_CODE);
        } else {
            MyTime myTime = (MyTime) parameter;
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, myTime.getHours());
            c.set(Calendar.MINUTE, myTime.getMinutes());
            c.set(Calendar.SECOND, myTime.getSeconds());

            Time time = new Time(c.getTime().getTime());

            ps.setTime(i, time);
        }
    }
}
