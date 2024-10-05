package com.fintech.pob.pendinghistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.pob.domain.account.dto.client.ClientAccountTransferResponseDTO;
import com.fintech.pob.domain.account.dto.request.AccountTransferRequestDTO;
import com.fintech.pob.domain.account.service.AccountService;
import com.fintech.pob.domain.pendinghistory.dto.PendingHistoryDTO;
import com.fintech.pob.domain.pendinghistory.service.PendingHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PendingHistoryServiceTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PendingHistoryService pendingHistoryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

//    @Test
//    public void testSavePendingHistory() throws Exception {
//        // 테스트 데이터 생성
//        PendingHistoryDTO dto = new PendingHistoryDTO();
//        dto.setDepositAccountNo("123456789");
//        dto.setTransactionBalance(1000L);
//        dto.setWithdrawalAccountNo("987654321");
//        dto.setDepositTransactionSummary("Deposit Memo");
//        dto.setWithdrawalTransactionSummary("Withdrawal Memo");
//
//        int notificationId = 3;
//        String redisKey = "PendingHistory:" + notificationId;
//
//        // 테스트 실행: 실제 Redis에 데이터 저장
//        pendingHistoryService.savePendingHistory(notificationId, dto);
//
//        // 저장된 데이터 확인
//        ListOperations<String, String> listOperations = redisTemplate.opsForList();
//        List<String> values = listOperations.range(redisKey, 0, -1); // 저장된 리스트의 모든 값 가져오기
//        for(int i = 0; i < values.size(); i++) {
//            System.out.println(values.get(i));
//        }
//
//        // JSON 문자열로 직렬화한 예상 결과
//        Map<String, String> expectedData = new HashMap<>();
//        expectedData.put("depositAccountNo", dto.getDepositAccountNo());
//        expectedData.put("transactionBalance", String.valueOf(dto.getTransactionBalance()));
//        expectedData.put("withdrawalAccountNo", dto.getWithdrawalAccountNo());
//        expectedData.put("depositTransactionSummary", dto.getDepositTransactionSummary());
//        expectedData.put("withdrawalTransactionSummary", dto.getWithdrawalTransactionSummary());
//        String expectedRedisValue = objectMapper.writeValueAsString(expectedData);
//
//        // 검증
//        assertThat(values).isNotEmpty(); // 리스트가 비어 있지 않은지 확인
//        assertThat(values.get(0)).isEqualTo(expectedRedisValue); // 저장된 값이 예상된 값과 같은지 확인
//
//        // TTL 확인
//        Long expire = redisTemplate.getExpire(redisKey, TimeUnit.HOURS);
//        assertThat(expire).isNotNull(); // TTL이 설정되어 있는지 확인
//        assertThat(expire).isGreaterThan(0); // TTL이 0보다 큰지 확인
//    }

    @Test
    public void testAcceptPendingHistory() throws Exception {
        // 데이터 준비
        int notificationId = 3;
        String redisKey = "PendingHistory:" + notificationId;
        Map<String, String> pendingHistoryData = new HashMap<>();
        pendingHistoryData.put("depositAccountNo", "123456789");
        pendingHistoryData.put("transactionBalance", "1000");
        pendingHistoryData.put("withdrawalAccountNo", "987654321");
        pendingHistoryData.put("depositTransactionSummary", "Deposit Memo");
        pendingHistoryData.put("withdrawalTransactionSummary", "Withdrawal Memo");

        String redisValue = objectMapper.writeValueAsString(pendingHistoryData);

        // 실제 Redis에 데이터 저장
        redisTemplate.opsForList().rightPush(redisKey, redisValue);

        // 테스트 실행
        pendingHistoryService.acceptPendingHistory(notificationId);

        // 데이터 검증
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        List<String> values = listOperations.range(redisKey, 0, -1);
        assertThat(values).isEmpty(); // 데이터가 삭제되었는지 확인
    }
}

