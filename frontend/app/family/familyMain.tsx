import { useRouter } from "expo-router";
import { useState, useEffect } from "react";
import { View, Text, Image, Button, ImageBackground, TouchableOpacity, SafeAreaView } from "react-native";
import { Link } from "expo-router";
import LinkToAddWard from "../../src/ui/components/LinkToAddWard";
import sendMoney from "./sendMoney";
import DealHistory from "./dealHistory";
import smallLogo from "../../src/assets/SmallLogo.png";
import mail from '../../src/assets/mail.png'
import Toast from "react-native-toast-message";
import { logoutUser, subscriptionCheck } from "../../src/services/api";
import { useDispatch } from "react-redux";
import { logout, setNickName } from "../../src/store/userSlice";

const FamilyMain = () => {

  const dispatch = useDispatch();

  /* 내가 보호자인 경우, 피보호자를 가져온다. */
  const [flag, setFlag] = useState(false)

  const router = useRouter();

  const logoutTry = async() => {
    try{
      const response = await logoutUser();
      
      console.log(response)
      dispatch(logout());

        Toast.show({
          type: 'success',
          text1: '로그아웃 성공!',
          text2: '정상적으로 로그아웃 되었습니다'
        })
      router.push('/')
  }
  catch(error){
      console.log(error)
      Toast.show({
          type: 'error',
          text1: '로그아웃 실패',
          text2: '정보를 다시 확인해주세요.'
        })
  }
  }

  /*
  ★★★★★★추가해야 할 내용★★★★★★
  1. 대표 계좌 조회 요청
  - 있으면 보여주고, 없으면 대표 계좌 등록 메세지 보여주기

  */

  interface wardState{
    accountNo: null | string, 
    created: string, 
    subscriptionType: number, 
    updated: string, 
    userId: string, 
    userKey: string, 
    userName: string, 
    userPassword: string
  }

  // 피보호자 정보
  const [wardInfo, setWardInfo] = useState<wardState>(
    {
      accountNo: '', 
      created: '', 
      subscriptionType: 0, 
      updated: '', 
      userId: '', 
      userKey: '', 
      userName: '', 
      userPassword: ''
    }
  )


  useEffect(() => {

    // 구독 관계 있는지 확인
    const subCheck = async() => {
      try{
        const response = await subscriptionCheck()
        const checking = response.data
        console.log(response)
        if (checking == null){
          setFlag(false) // 없으면 등록 화면 보여주기
        } else{
          const Info = response.data.protectUser
          setWardInfo(Info)
          dispatch(setNickName(wardInfo.userName));
          setFlag(true) // 있으면 관계 보여주기
        }
      } catch(error){
        console.log(error)
      }
    }
    subCheck() // 구독 관계 요청

    return () => {}
    },[]);

  // 대표 계좌 보여줄 지 확인하기
  const [mainAccount, setMainAccount] = useState('1')

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
        </View>
      </SafeAreaView>
      <View className="bg-gray-200 flex justify-center items-center">
        {(mainAccount=='1')? (        
          <Link className='h-6 rounded-3xl justify-center m-4 text-center font-bold' 
             href={
                {pathname:'/family copy/totalAccount'}
                }>테스트용 00 계좌 : 102312-300-231245</Link>) 
                :<Link className='h-6 rounded-3xl justify-center m-4 text-center font-bold' 
                href={
                   {pathname:'/family copy/totalAccount'}
                   }>계좌 등록하기</Link>}
      </View>

      {/* 피보호자 존재 - 메인 레이아웃 / 아니면 추가할 수 있는 레이아웃 구성 */}
      {(flag==true) ? (
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
                        params:{accounting:wardInfo.accountNo, banking:wardInfo.userKey, naming:wardInfo.userName}}
                      }>{wardInfo.userName}</Link> 
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
                <TouchableOpacity 
                  className='w-24 h-8 m-5 rounded-3xl justify-center bg-gray-300'
                  onPress={logoutTry} 
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
          </View>
          <TouchableOpacity 
                  className='w-24 h-8 m-5 rounded-3xl justify-center bg-gray-300'
                  onPress={() => router.push('/')} 
                  >
              <Text className='text-center'>로그아웃</Text>
              </TouchableOpacity>
          </View>
        </View>
          )}
    </SafeAreaView>
  );
};

export default FamilyMain;
