package com.zayaanit.todoist.testController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.zayaanit.module.sections.CreateSectionReqDto;
import com.zayaanit.module.sections.SectionResDto;
import com.zayaanit.module.sections.SectionService;
import com.zayaanit.module.sections.UpdateSectionReqDto;
import com.zayaanit.todoist.BaseAuthenticatedControllerTest;

public class SectionControllerTest extends BaseAuthenticatedControllerTest {

	@MockitoBean
	SectionService sectionService;

	@Test
	void testGetAllSections() throws Exception {
		mockMvc.perform(get(BASE_SECTION_URL).param("projectId", "1").headers(authorizedHeaders()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testFindSectionById() throws Exception {
		mockMvc.perform(get(BASE_SECTION_URL + "/{id}", 1L).headers(authorizedHeaders()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testCreateSection() throws Exception {
		CreateSectionReqDto reqDto = new CreateSectionReqDto();
		reqDto.setName("Planning");
		reqDto.setProjectId(1L);

		mockMvc.perform(post(BASE_SECTION_URL).headers(authorizedHeaders()).contentType(MediaType.APPLICATION_JSON)
				.content(toJson(reqDto))).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void testUpdateSection() throws Exception {
		UpdateSectionReqDto reqDto = new UpdateSectionReqDto();
		reqDto.setId(1L);
		reqDto.setName("Execution");

		SectionResDto resDto = new SectionResDto();
		resDto.setId(1L);
		resDto.setName("Execution");
		// Assuming the service is mocked to return the updated section
		when(sectionService.updateSection(any(UpdateSectionReqDto.class))).thenReturn(resDto);

		mockMvc.perform(put(BASE_SECTION_URL).headers(authorizedHeaders()).contentType(MediaType.APPLICATION_JSON)
				.content(toJson(reqDto))).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
