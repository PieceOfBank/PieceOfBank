import { useRouter } from "expo-router";
import { useState } from "react";
import { View, Text, Image, Button, ImageBackground, TouchableOpacity, SafeAreaView } from "react-native";
import { Link } from "expo-router";
import LinkToAddWard from "../../src/ui/components/LinkToAddWard";
import sendMoney from "./sendMoney";
import DealHistory from "./dealHistory";
import smallLogo from "../../src/assets/SmallLogo.png";
import mail from '../../src/assets/mail.png'

const FamilyMain = () => {
  const router = useRouter();

  /* 내가 보호자인 경우, 피보호자를 가져온다. */
  let flag: boolean = true;

  // 임시 피보호자 정보
  const wardInfo= { directoryId : 1, userKey: '1', accountNo: '123456789', institutionCode: 1, name: '엄마' }

  return (
    <SafeAreaView>
      {/* <Header /> */}
      <SafeAreaView>
        <View className="h-16 my-5 bg-blue-400 dark:bg-blue-100 flex-row justify-between items-center pt-3 px-2">
          <View className="w-12 h-12"></View>
          <Image source={smallLogo} className="w-12 h-12" />
          <TouchableOpacity
            className="w-12 h-12 pt-2 pr-16"
            onPress={() => router.push('/family copy/reqSendMoney')} >
            <Image source={mail}  />
          </TouchableOpacity>
          {/* <Text>hello my Header</Text> */}
        </View>
      </SafeAreaView>
      <View className="bg-gray-200 flex justify-center items-center">
        {flag? (        
          <Link className='h-6 rounded-3xl justify-center m-4 text-center font-bold' 
             href={
                {pathname:'/family copy/totalAccount'}
                }>테스트용 00 계좌 : 102312-300-231245</Link>) 
                :<Link className='h-6 rounded-3xl justify-center m-4 text-center font-bold' 
                href={
                   {pathname:'/family copy/totalAccount'}
                   }>계좌 등록하기</Link>}

        {/* <TouchableOpacity 
          className='w-48 h-8 mx-1 rounded justify-center bg-gray-500'
          onPress={() => router.push('/family copy/reqSendMoney')} 
          >
          <Text className='text-white text-center font-bold'>알림함</Text></TouchableOpacity> */}
      </View>

      {/* 피보호자 존재 - 메인 레이아웃 / 아니면 추가할 수 있는 레이아웃 구성 */}
      {flag ? (
        <View>
          {/* 보호자 Card*/}
          <View>
            <View className='justify-center items-center'>
              <View className='h-8'></View>
              <View className="w-56 h-56 m-4 pt-8 pb-4 bg-gray-200 flex justify-center items-center rounded-3xl">
                <View className='border bg-white'>
                <Image className="w-32 h-32 bg-teal-400 mt-4" source={require('../../assets/smile.png')}></Image>
                </View>
                <Link className='w-32 h-8 pt-1 rounded-3xl justify-center items-center bg-teal-100 my-4 text-center rounded-3xl font-bold' 
                  href={
                      {pathname:'/family copy/dealHistory', 
                        params:{account:wardInfo.accountNo, bank:wardInfo.institutionCode, name:wardInfo.name}}
                      }>{wardInfo.name}</Link>
              </View>
                <TouchableOpacity 
                  className='w-48 h-12 m-5 rounded-3xl justify-center bg-gray-300'
                  onPress={() => router.push('/family copy/MoneyThreshHold')} 
                  >
                    <Text className='text-center text-base font-bold'>금액 한도 설정</Text></TouchableOpacity>
                <TouchableOpacity 
                  className='w-48 h-12 m-5 rounded-3xl justify-center bg-gray-300'
                  onPress={() => router.push('/family copy/DirectoryList')} 
                  >
                <Text className='text-center text-base font-bold'>부모 대상 송금 관리</Text></TouchableOpacity>
                {/* <View className='h-40'></View> */}
                <TouchableOpacity 
                  className='w-24 h-8 m-5 rounded-3xl justify-center bg-gray-300'
                  onPress={() => router.push('/')} 
                  >
                <Text className='text-center'>로그아웃</Text></TouchableOpacity>
            </View>
          </View>
        </View>
      ) : (
        <View>
          {/* <LinkToAddWard /> */}
          <View className='justify-center items-center'>
            <View className='h-28'></View>
            <View className="w-56 h-56 m-4 pt-8 pb-4 bg-gray-200 flex justify-center items-center rounded-3xl">
              <View className='border bg-white'>
              <Image className="w-32 h-32 bg-teal-400 mt-4" source={require('../../assets/smile.png')}></Image>
              </View>
              <TouchableOpacity className='m-2 py-1 px-4 bg-sky-400 rounded-3xl' onPress={() => router.push('/family copy/addWard')}>
                  <Text className='text-white'>피보호자 등록 신청</Text>
              </TouchableOpacity>
              <TouchableOpacity 
                  className='w-24 h-8 m-5 rounded-3xl justify-center bg-gray-300'
                  onPress={() => router.push('/')} 
                  >
              <Text className='text-center'>로그아웃</Text></TouchableOpacity>
          </View>
          </View>
        </View>
          )}
    </SafeAreaView>
  );
};

export default FamilyMain;
