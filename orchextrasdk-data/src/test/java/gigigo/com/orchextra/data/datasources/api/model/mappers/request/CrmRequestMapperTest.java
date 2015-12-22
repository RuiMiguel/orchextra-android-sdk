package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.entities.Crm;
import com.gigigo.orchextra.domain.entities.GenderType;

import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.resquests.ApiCrm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CrmRequestMapperTest {

    private java.util.Date getCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }

    @Test
    public void testModelToDataMaleWithKeywords() throws Exception {
        List<String> keywords = new ArrayList<>();
        keywords.add("palabra");
        Crm crm = new Crm("1234", GenderType.MALE, getCalendar(2012, 9, 21, 11, 22, 34), keywords);

        CrmRequestMapper mapper = new CrmRequestMapper();
        ApiCrm apiCrm = mapper.modelToData(crm);

        assertEquals("1234", apiCrm.getCrmId());
        assertEquals("m", apiCrm.getGender());
        assertEquals("2012-10-21T11:22:34Z", apiCrm.getBirthDate());
        assertEquals(1, apiCrm.getKeywords().size());
        assertEquals("palabra", apiCrm.getKeywords().get(0));
    }

    @Test
    public void testModelToDataFeMaleWithoutKeywords() throws Exception {
        Crm crm = new Crm("1234", GenderType.FEMALE, getCalendar(2012, 9, 21, 11, 22, 34), new ArrayList<String>());

        CrmRequestMapper mapper = new CrmRequestMapper();
        ApiCrm apiCrm = mapper.modelToData(crm);

        assertEquals("1234", apiCrm.getCrmId());
        assertEquals("f", apiCrm.getGender());
        assertEquals("2012-10-21T11:22:34Z", apiCrm.getBirthDate());
        assertEquals(0, apiCrm.getKeywords().size());
    }

    @Test
    public void testModelToDataNDAndNullKeywords() throws Exception {
        Crm crm = new Crm("1234", GenderType.ND, new Date(0), null);

        CrmRequestMapper mapper = new CrmRequestMapper();
        ApiCrm apiCrm = mapper.modelToData(crm);

        assertEquals("1234", apiCrm.getCrmId());
        assertEquals("n", apiCrm.getGender());
        assertEquals("1970-01-01T01:00:00Z", apiCrm.getBirthDate());
        assertNull(apiCrm.getKeywords());
    }
}