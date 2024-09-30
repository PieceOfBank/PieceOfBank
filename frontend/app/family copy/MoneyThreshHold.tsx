import { View, Text, SafeAreaView, TextInput, Button } from "react-native";
import { Link } from "expo-router";
import { Header } from "react-native/Libraries/NewAppScreen";
import { useState } from "react";
import Toast from 'react-native-toast-message';

const MoneyTrheshHold = () => {
    const [oneLimit, setOneLimit] = useState<string>("");
  const [todayLimit, setTodayLimit] = useState<string>("");

  /* 알림팝업 Logic */
  const handleBtn = () => {
    Toast.show({
      type: 'success',
      text1: 'Hello',
      text2: 'This is some something 👋'
    })
  }

  return (
    <View>
      <Header />

      <View className='justify-center items-center bg-green-100 p-4'>
        <Text className='text-2xl'>금액 한도 설정</Text>
        <SafeAreaView>
          <Text className="my-2">1회</Text>
          <View className='flex-row bg-sky-100'>
            <TextInput className="my-1 w-60 bg-white" onChangeText={(limit) => setOneLimit(limit)}></TextInput>
            <Text>원</Text>
          </View>

          <Text className="my-2">하루 총</Text>
          <View className='flex-row bg-sky-100'>
          <TextInput
            className="my-1 w-60 bg-white"
            onChangeText={(limit) => setTodayLimit(limit)}
          ></TextInput><Text>원</Text>
          </View>

        </SafeAreaView>
        <Button title="press Me" onPress={handleBtn} />
        <Link className="my-2" href={""}>
          요청 보내기
        </Link>
      </View>
    </View>
  );
};

export default MoneyTrheshHold;