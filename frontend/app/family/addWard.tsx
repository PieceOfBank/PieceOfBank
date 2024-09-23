import { View, Text, SafeAreaView, TextInput } from "react-native";
import { Link } from "expo-router";
import { Header } from "react-native/Libraries/NewAppScreen";
import { useState } from "react";

const addWard = () => {
  const [wardId, setWardId] = useState<string>("");
  const [relation, setRelation] = useState<string>("");

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
        <Link className="my-2" href={""}>
          요청 보내기
        </Link>
      </View>
    </View>
  );
};

export default addWard;
