package com.example.tasktodayapp

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings.TextSize
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasktodayapp.model.Tarefa.Tarefa
import com.example.tasktodayapp.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
        }
    }
}

@Composable
fun MainScreenContent(drawerState: DrawerState){
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    val scope = rememberCoroutineScope()
    //val tabIndex = by remember {  mutableStateOf(0)  }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Preto,
                contentColor = Color.White,
                title = { Text(text = "TaskTodayApp")},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Drawer Menu"
                        )
                    }
                }
            )
        },
        drawerBackgroundColor = Preto,
        drawerGesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Box(
                modifier = Modifier
                    .background(Preto)
                    .height(16.dp)
            ){
                Text(text = "Opções!", color = Color.White, fontWeight = FontWeight.ExtraBold)
            }
            Column(){
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Opção de Menu 1", color = Color.White)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Opção de Menu 2", color = Color.White)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Opção de Menu 3", color = Color.White)
            }
        },
        content = {
                paddingValues -> Log.i("paddingValues", "$paddingValues")
            Column(
                modifier = Modifier
                    .background(Cinza)
                    .fillMaxSize()
            ) {
                MySearchField(modificador = Modifier.fillMaxWidth())

                val tProvaDeCalculo = Tarefa(
                    "Estudar Prova de Calculo",
                    "Captulo",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val tProvaDeKotlin = Tarefa(
                    "Estudar Prova de Kotlin",
                    "Captulo",
                    Date(),
                    Date(),
                    status = 0.0
                )

                var minhaListaDeTarefas = listOf<Tarefa>(tProvaDeCalculo, tProvaDeKotlin)

                MyTaskWidgetList(minhaListaDeTarefas)
            }//Column
        },//content
        bottomBar = {
            BottomAppBar(
                backgroundColor = Preto,
                contentColor = Color.White,
                content = { Text("Autor: Ana Luísa Augusto ", fontWeight = FontWeight.ExtraBold)}
            )
        },

        isFloatingActionButtonDocked = false,
        floatingActionButton = { ExtendedFloatingActionButton(
            backgroundColor = preto2,
            contentColor = Color.White,
            icon = {
                   Icon(
                       imageVector = Icons.Default.AddCircle,
                       contentDescription = "Add Task"
                   )
            },
            text = { Text("ADD")  },
            onClick = { /*TODO*/ })

        }
    ) //Scaffold
} //MainScreenContent

@Composable
fun MyTaskWidgetList(listaDeTarefas: List<Tarefa>){
    listaDeTarefas.forEach(
        action = { MyTaskWidget(modificador = Modifier.fillMaxWidth(), taredasASerMostrada = it) }
    )
} //MyTaskWidgetList

@Composable
fun MySearchField(modificador: Modifier){
    TextField(
        value = "",
        onValueChange = {},
        modifier = modificador,
        placeholder = { Text(text = "Pesquisar tarefas")},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon")
        }
    )
} //MySearchField

@Composable
fun MyTaskWidget(
    modificador: Modifier,
    taredasASerMostrada: Tarefa
){
    val dateFormatter = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())

        Column(modifier = modificador
            .padding(3.dp)
        ){
            Row(modifier = modificador) {
                Column(modifier = Modifier
                    .width(150.dp)
                    .padding(10.dp)){
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Icons of a pendent task")
                    Text(
                        text = dateFormatter.format(taredasASerMostrada.pzoFinal),
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 12.sp,
                    )
                }
                Column(){
                    Text(
                        text = taredasASerMostrada.nome,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                    )

                    Text(
                        text = taredasASerMostrada.detalhes,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        color = preto2,
                    )
                }

            Spacer(modifier = Modifier.height(16.dp))
            }

    }
    Spacer(modifier = Modifier.height(16.dp))
} //MyTaskWidget


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
}