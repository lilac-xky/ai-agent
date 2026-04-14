package com.lilac.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;

/**
 * 恋爱APP RAG自定义顾问工厂
 */
@Slf4j
public class LoveAppRagCustomAdvisorFactory {

    public static Advisor createLoveAppRagCustomAdvisor(VectorStore vectorStore, String status) {
        VectorStoreDocumentRetriever vectorStoreDocumentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .filterExpression(new FilterExpressionBuilder()
                        .eq("status", status)
                        .build())
                .similarityThreshold(0.7)
                .topK(3)
                .build();
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(vectorStoreDocumentRetriever)
                .queryAugmenter(LoveAppContextualQueryAugmenterFactory.createInstance())
                .build();
    }
}
