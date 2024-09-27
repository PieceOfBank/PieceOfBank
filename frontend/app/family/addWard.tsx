import { View, Text, SafeAreaView, TextInput, Button } from "react-native";
import { Link } from "expo-router";
import { Header } from "react-native/Libraries/NewAppScreen";
import { useState } from "react";
import Toast from 'react-native-toast-message';

const AddWard = () => {
  const [wardId, setWardId] = useState<string>("");
  const [relation, setRelation] = useState<string>("");

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
      <Text className="">보호자 등록 신청</Text>

      <View>
        <SafeAreaView>
          <Text className="my-2">아이디</Text>
          <TextInput className="my-1 w-60 bg-white" onChangeText={(id) => setWardId(id)}></TextInput>
          <Text className="my-2">비밀번호</Text>
          <TextInput
            className="my-1 w-60 bg-white"
            secureTextEntry={true}
            onChangeText={(password) => setRelation(password)}
          ></TextInput>
        </SafeAreaView>
        <Button title="press Me" onPress={handleBtn} />
        <Link className="my-2" href={""}>
          요청 보내기
        </Link>
      </View>
    </View>
  );
};

export default AddWard;
