import { useRouter } from "expo-router";
import { useState } from "react";
import { View, Text, Image, Button, ImageBackground, } from "react-native";
import { Link } from "expo-router";
import Header from "../../src/ui/components/Header";
import LinkToAddWard from "../../src/ui/components/LinkToAddWard";
import sendMoney from "./sendMoney";
import DealHistory from "./dealHistory";

const FamilyMain = () => {
  const router = useRouter();

  /* 내가 보호자인 경우, 피보호자를 가져온다. */
  let flag: boolean = true;

  // 임시 피보호자 정보
  const wardInfo= { directoryId : 1, userKey: '1', accountNo: '123456789', institutionCode: 1, name: '엄마' }

  return (
    <View>
      <Header />
      <View className="bg-gray-200 flex justify-center items-center">
        <Text>테스트용 00 계좌 : 102312-300-231245</Text>
        <Link className='w-20 h-6 rounded-3xl justify-center bg-sky-200 m-4 text-center rounded-3xl font-bold' 
             href={
                {pathname:'/family copy/totalAccount'}
                }>계좌 등록</Link>
      </View>

      {/* 피보호자 존재 - 메인 레이아웃 / 아니면 추가할 수 있는 레이아웃 구성 */}
      {flag ? (
        <View>
          {/* 보호자 Card*/}
          <View>
          <View className="w-48 h-48 m-4 bg-white justify-center items-center rounded-3xl">
             <Image className="w-32 h-32 bg-teal-400 mt-4" source={require('../../assets/favicon.png')}></Image>
             <Link className='w-20 h-6 rounded-3xl justify-center bg-sky-200 m-4 text-center rounded-3xl font-bold' 
             href={
                {pathname:'/family copy/dealHistory', 
                  params:{account:wardInfo.accountNo, bank:wardInfo.institutionCode, name:wardInfo.name}}
                }>{wardInfo.name}</Link>
         </View>
            <Text>보호자 카드는 여기에..</Text>
          </View>
        </View>
      ) : (
        <View>
          <LinkToAddWard />
          <Link className='w-40 h-6 rounded-3xl justify-center bg-sky-200 m-4 text-center rounded-3xl font-bold' 
             href={{pathname:'/family copy/addWard'}}><Text>피보호자 등록 신청</Text></Link>
        </View>
          )}
          
          <Button title="금액 한도 설정" onPress={()=>router.push('/family copy/MoneyThreshHold')}></Button>
          <Button title="부모 대상 송금 관리"></Button>
    </View>
  );
};

export default FamilyMain;
