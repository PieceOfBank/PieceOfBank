/* 
    1. ExpoToken
    2. isLogged
    3. email

*/
import { configureStore } from '@reduxjs/toolkit';
import tokenReducer from './tokenSlice'; // 방금 생성한 slice 불러오기
import userKeyReducer from './userKeySlice';

const store = configureStore({
  reducer: {
        getToken: tokenReducer, // token 리듀서 설정
        getUserKey : userKeyReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>; // RootState 타입 정의

export default store;
