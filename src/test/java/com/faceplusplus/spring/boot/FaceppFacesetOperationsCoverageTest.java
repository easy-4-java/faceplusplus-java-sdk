package com.faceplusplus.spring.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.faceplusplus.spring.boot.req.*;
import com.faceplusplus.spring.boot.resp.*;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Coverage tests for FaceppFacesetOperations and FaceppFacesetAsyncOperations.
 * Uses a testable subclass of FaceppOkHttp3Template to avoid network calls.
 */
class FaceppFacesetOperationsCoverageTest {

    private FaceppTemplate template;
    private FaceppProperties properties;
    private StubOkHttp3Template stubHttpTemplate;
    private FaceppFacesetOperations operations;
    private FaceppFacesetAsyncOperations asyncOperations;

    @BeforeEach
    void setUp() {
        properties = new FaceppProperties();
        properties.setAppId("test-key");
        properties.setAppCertificate("test-secret");
        stubHttpTemplate = new StubOkHttp3Template(new OkHttpClient(), new ObjectMapper(), properties);
        template = new FaceppTemplate(stubHttpTemplate, properties);
        operations = new FaceppFacesetOperations(template);
        asyncOperations = new FaceppFacesetAsyncOperations(template);
    }

    @Test
    void shouldCreateFaceset() throws IOException {
        FacesetBo bo = new FacesetBo();
        bo.setDisplayName("TestSet");
        bo.setOuterId("outer-1");
        bo.setTags("tag1");
        bo.setFaceTokens("token1,token2");
        bo.setUserData("data");
        bo.setForceMerge(1);
        FacesetCreateResponse result = operations.createFaceset(bo);
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACESET_CREATE.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldDeleteFaceset() throws IOException {
        FacesetDeleteBo bo = new FacesetDeleteBo();
        bo.setOuterId("outer-1");
        bo.setCheckEmpty(0);
        FacesetCreateResponse result = operations.deleteFaceset(bo);
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACESET_DELETE.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldUpdateFaceset() throws IOException {
        FacesetUpdateBo bo = new FacesetUpdateBo();
        bo.setFaceToken("token-1");
        bo.setOuterId("outer-1");
        bo.setNewOuterId("new-outer-1");
        bo.setDisplayName("NewName");
        bo.setTags("tag1");
        bo.setUserData("data");
        FacesetCreateResponse result = operations.updateFaceset(bo);
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACESET_UPDATE.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldGetFacesetListWithMultipleTags() throws IOException {
        FacesetListResponse result = operations.getFacesetList(0, "tag1", "tag2");
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACESET_LIST.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldGetFacesetListWithSingleTag() throws IOException {
        FacesetListResponse result = operations.getFacesetList(0, "tag1");
        assertNotNull(result);
    }

    @Test
    void shouldGetFacesetListWithNoTags() throws IOException {
        FacesetListResponse result = operations.getFacesetList(0);
        assertNotNull(result);
    }

    @Test
    void shouldGetFacesetListWithNullTags() throws IOException {
        FacesetListResponse result = operations.getFacesetList(0, (String[]) null);
        assertNotNull(result);
    }

    @Test
    void shouldGetFacesetByToken() throws IOException {
        FacesetDetailResponse result = operations.getFacesetByToken("token-1", 0);
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACESET_LIST.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldGetFacesetByOuterId() throws IOException {
        FacesetDetailResponse result = operations.getFacesetByOuterId("outer-1", 0);
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACESET_DETAIL.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldAddFaceWithToken() throws IOException {
        FaceAddResponse result = operations.addFaceWithToken("token-1", "face1", "face2");
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACE_ADD.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldAddFaceWithOuterId() throws IOException {
        FaceAddResponse result = operations.addFaceWithOuterId("outer-1", "face1");
        assertNotNull(result);
    }

    @Test
    void shouldRemoveFaceByToken() throws IOException {
        FaceRemoveResponse result = operations.removeFaceByToken("token-1", "face1", "face2");
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACE_REMOVE.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldRemoveFaceByOuterId() throws IOException {
        FaceRemoveResponse result = operations.removeFaceByOuterId("outer-1", "face1");
        assertNotNull(result);
    }

    @Test
    void shouldCreateFace() throws IOException {
        FaceSetUserIdResponse result = operations.createFace("face-token-1", "user-1");
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACE_SET_USERID.getUrl(), stubHttpTemplate.getLastUrl());
    }

    @Test
    void shouldGetFaceDetail() throws IOException {
        FaceDetailResponse result = operations.getFaceDetail("face-token-1");
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACE_GET_DETAIL.getUrl(), stubHttpTemplate.getLastUrl());
    }

    // Async operations

    @Test
    void shouldAsyncAddFaceWithToken() throws IOException {
        assertDoesNotThrow(() -> asyncOperations.asyncAddFaceWithToken("token-1", new String[]{"face1"}, resp -> {}));
    }

    @Test
    void shouldAsyncAddFaceWithOuterId() throws IOException {
        assertDoesNotThrow(() -> asyncOperations.asyncAddFaceWithOuterId("outer-1", new String[]{"face1"}, resp -> {}));
    }

    @Test
    void shouldAsyncRemoveFaceByToken() throws IOException {
        assertDoesNotThrow(() -> asyncOperations.asyncRemoveFaceByToken("token-1", new String[]{"face1"}, resp -> {}));
    }

    @Test
    void shouldAsyncRemoveFaceByOuterId() throws IOException {
        assertDoesNotThrow(() -> asyncOperations.asyncRemoveFaceByOuterId("outer-1", new String[]{"face1"}, resp -> {}));
    }

    @Test
    void shouldGetFaceStatusByTaskId() throws IOException {
        FaceStatusResponse result = asyncOperations.getFaceStatusByTaskId("task-1");
        assertNotNull(result);
        assertEquals(FaceppApiAddress.FACE_STATUS_ASYNC.getUrl(), stubHttpTemplate.getLastUrl());
    }

    /**
     * Stub subclass of FaceppOkHttp3Template that returns default instances
     * without making real HTTP calls.
     */
    static class StubOkHttp3Template extends FaceppOkHttp3Template {
        private String lastUrl;
        private Map<String, Object> lastParams;

        StubOkHttp3Template(OkHttpClient client, ObjectMapper mapper, FaceppProperties props) {
            super(client, mapper, props);
        }

        String getLastUrl() { return lastUrl; }
        Map<String, Object> getLastParams() { return lastParams; }

        @Override
        public <T extends FaceppResponse> T post(String url, Map<String, Object> headers, Map<String, Object> params, Class<T> rtClass) throws IOException {
            this.lastUrl = url;
            this.lastParams = params;
            try {
                T res = rtClass.getDeclaredConstructor().newInstance();
                res.setCode(200);
                return res;
            } catch (Exception e) {
                throw new IOException(e);
            }
        }

        @Override
        public <T extends FaceppResponse> T doPartRequest(String httpUrl, Map<String, Object> params, Class<T> rtClass) throws IOException {
            this.lastUrl = httpUrl;
            this.lastParams = params;
            try {
                T res = rtClass.getDeclaredConstructor().newInstance();
                res.setCode(200);
                return res;
            } catch (Exception e) {
                throw new IOException(e);
            }
        }

        @Override
        public <T extends FaceppResponse> void doAsyncRequest(String url, HttpMethod method, Consumer<T> success, Class<T> rtClass) throws IOException {
            this.lastUrl = url;
        }
    }
}
