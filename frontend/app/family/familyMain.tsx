import { useRouter } from "expo-router";
import { useState } from "react";
import { View, Text, Image, Button } from "react-native";
import { Header } from "react-native/Libraries/NewAppScreen";

const familyMain = () => {

    /* 내가 보호자인 경우, 피보호자를 가져온다. */
    const router = useRouter();

    return (
        <View>
            <Header />
            <View className="bg-gray-200 flex justify-center items-center">
                <Text>테스트용 00 계좌 : 102312-300-231245</Text>
            </View>

            {/* 피보호자 존재 - 메인 레이아웃 */}
            <View>
                {/* 보호자 Card*/}
                <View></View>

                <Button title="금액 한도 설정"></Button>
                <Button title="부모 대상 송금 관리"></Button>
            </View>
            {/* 피보호자 존재 X - 보호자 추가로 이동 기능 */}
        </View>
    );
};

export default familyMain;