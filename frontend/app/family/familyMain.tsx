import { useRouter } from "expo-router";
import { useState } from "react";
import { View, Text, Image, Button, ImageBackground } from "react-native";
import Header from "../../src/ui/components/header";

const FamilyMain = () => {
  const router = useRouter();

  /* 내가 보호자인 경우, 피보호자를 가져온다. */
  let flag: boolean = false;

  return (
    <View>
      <Header />
      <View className="bg-gray-200 flex justify-center items-center">
        <Text>테스트용 00 계좌 : 102312-300-231245</Text>
      </View>

      {/* 피보호자 존재 - 메인 레이아웃 / 아니면 추가할 수 있는 레이아웃 구성 */}
      {flag ? (
        <View>
          {/* 보호자 Card*/}
          <View>
            <Text>보호자 카드는 여기에..</Text>
          </View>

          <Button title="금액 한도 설정"></Button>
          <Button title="부모 대상 송금 관리"></Button>
        </View>
      ) : (
        <View>
          <Text>보호자가 없습니다.</Text>
        </View>
      )}
    </View>
  );
};

export default FamilyMain;
