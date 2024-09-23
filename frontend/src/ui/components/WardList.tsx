import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, TouchableOpacity, Image, FlatList } from 'react-native';
import React, { useState, useEffect } from 'react';
import { useRouter } from 'expo-router';
import * as ScreenOrientation from 'expo-screen-orientation';

const WardListForm = () => {

    const router = useRouter();

    interface CareItem {
        id: string;
        name: string;
    }

     // 현재 페이지 기준
     const [nowPage, setNowPage] = useState(0)

     // 한 페이지에 보여줄 카드 수
     const pageNum = 3
 
     // 다음 페이지로 이동
     const nextPage = (value: number) => {
         if ((nowPage+1) * pageNum < allList.length){
             setNowPage(nowPage + 1)
         }
         }
         
     // 이전 페이지로 이동
     const prevPage = (value: number) => {
         if (nowPage > 0) {
             setNowPage(nowPage-1)
         }
     }
 
     // 임시 리스트 (전체 연락처)
     const careList: CareItem [] = [{id:'1', name:'딸'}, {id:'2', name:'아들'}, {id:'3', name:'미숙'}, {id:'4', name:'영숙'}, {id:'5', name:'정숙'}]
     
     // 임시 리스트 (고정 카드: 전체 기록 카드)
     const addList: CareItem = {id:'99', name:'전체 기록'}
 
     // 임시 리스트 (전체 연락처 + 고정)
     const allList : CareItem [] = [...careList, addList]
 
 
     // 카드 출력
     const careView = ({item}:{item:CareItem}) => 
         (<View className="w-48 h-48 m-4 bg-white justify-center items-center rounded-3xl">
             <Image className="w-32 h-32 bg-teal-400 mt-4" source={require('../../../assets/favicon.png')}></Image>
             {/* <TouchableOpacity 
                className='w-20 h-6 rounded-3xl justify-center bg-sky-200 m-4' 
                onPress={() => router.push({pathname:'/ward/transaction', params:{props:item.title}})}>
             <Text className='text-center rounded-3xl font-bold'>{item.title}</Text></TouchableOpacity>    */}
             <Link className='w-20 h-6 rounded-3xl justify-center bg-sky-200 m-4 text-center rounded-3xl font-bold' href={
                {pathname:'/ward/transaction', params:{name:item.name}}
                }>{item.name}</Link>
         </View>)
 
     // 한 페이지에 보여 줄 카드 리스트 잘라서 보여주기
     const pageList = allList.slice(nowPage * pageNum, (nowPage+1) * pageNum);
 
     return (

           <View className='flex-row'>
            
            {/* 화살표 이전(<) 버튼 */}
             <View>
                 <TouchableOpacity 
                 className={`w-10 h-10 rounded-3xl justify-center ml-4 mt-28 ${nowPage === 0? 'bg-gray-500' : 'bg-sky-200'}`} 
                 onPress={() => prevPage(nowPage)} 
                 disabled={nowPage === 0}>
                     <Text className='text-center rounded-3xl font-bold text-3xl'>&lt;</Text></TouchableOpacity>   
             </View>

             {/* 연락처 카드 컴포넌트 */}
             <FlatList 
             data={pageList}
             renderItem={careView}
             keyExtractor={item => item.id}
             horizontal={true}
             className="m-8"
             />

             {/* 화살표 다음(>) 버튼 */}
             <View>
                 <TouchableOpacity 
                 className={`w-10 h-10 rounded-3xl justify-center ml-4 mt-28 ${(nowPage + 1) * pageNum >= allList.length ? 'bg-gray-500' : 'bg-sky-200'}`}
                 onPress={() => nextPage(nowPage)} 
                 disabled={(nowPage + 1) * pageNum >= allList.length}>
                     <Text className='text-center rounded-3xl font-bold text-3xl'>&gt;</Text></TouchableOpacity>   
             </View>
           </View>
         
       );
}

export default WardListForm