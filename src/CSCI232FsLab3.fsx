

//************************
//REFERENCE CODE DON'T RUN
//************************
let trim s = (s:string).Trim()

type WEdge = 
    |WEdge of weight:int*start:string*endpoint:string
   
type AdjacencyMatrix = 
    {
        vertices : string array
        edges : WEdge list
    }

let buildAdjMatrix (txt:string)=
    txt.Split('\n')
    |> Seq.map trim
    |> Seq.filter (System.String.IsNullOrWhiteSpace >> not)
    |> Seq.map (fun s -> s.Split(' ') |> Array.map trim |> Array.filter (System.String.IsNullOrWhiteSpace >> not) )
    |> Seq.toArray
    |> fun a ->
        let header = a.[0]
        let edges =
            a.[1..]
            |> Seq.mapi (fun row data -> 
                let start = header.[row]
                data
                |> Seq.mapi (fun column cell -> header.[column],cell)
                |> Seq.choose(function | _,"-"-> None|name,weight -> WEdge(System.Int32.Parse weight,start,name)|>Some)
                )
            |>Seq.collect id
            |> Seq.toList 
        {vertices=header;edges=edges}
        
 
type Graph= 
    |Graph of name:string*children:Graph list
 
type PQue<'a> = 'a list

let enqueue e pQ=
    let rec loop pQ cont=
        match pQ with
        |[] -> [e] |>cont
        |h::_ when h>e -> e::pQ|>cont
        |h::t -> loop t (fun rs -> h::rs|>cont)
    loop pQ id

let dequeue pQ = 
    match pQ with 
    |[]-> None,pQ
    |h::t -> Some h,t


type PrimSetElement =
    |Edge of WEdge
    |Vertex of string



let flip f = (fun y x -> f x y)

//let Prim (am:AdjacencyMatrix) (s:string)=
//    let t = [Vertex s ]|>Set.ofList 
//    let vertexIndices = am.vertices |> Seq.mapi (fun i x -> x,i)|>Map.ofSeq
//    let enqueueEdges start pQ=
//        let row=vertexIndices.[start]
//        let vertexWeights= weights.[row]
//        vertexWeights 
//        |> Seq.mapi (fun c w -> 
//            let endpoint = am.vertices.[c]
//            WEdge(w,start,endpoint))
//        |> Seq.filter (function |WEdge (weight=weight) -> (weight>=0))
//        |> Seq.fold (flip enqueue) pQ
//    let pQ= enqueueEdges s []
//    let rec loop pQ (t : _ Set) =
//        match dequeue pQ with
//        | None,_ -> t 
//        | Some edge, pQ -> 
//            match edge with
//            |WEdge(_,_,ev) when not(t.Contains(Vertex ev)) ->
//                let t' = t |>Set.add (Vertex ev)|> Set.add (Edge edge)
//                let pQ'=enqueueEdges ev pQ
//                loop pQ' t'
//            |_ -> loop pQ t 
//    loop pQ t
    
let PrimJarnik (am:AdjacencyMatrix) (s:string) =
    let W = 
        am.edges|>Seq.groupBy (function|WEdge (start=s) -> s)
        |> Seq.map (fun (start,es) -> 
            let m = 
                es |> Seq.map (function|WEdge (endpoint = e; weight=w) -> e,w)
                |>Map.ofSeq 
            start,m)
        |>Map.ofSeq 
        |> fun m -> 
            fun u v -> 
                match Map.tryFind u m with 
                |None -> None
                |Some m' -> Map.tryFind v m'
    let rec loop (D: Map<string,int>) (connect: Map<string,string*string>) (Q: (int*string) list) (T: (string*string) list)=
        match Q with
        |[] -> T
        |(_,u)::rest ->
            let T' =
                match Map.tryFind u connect with
                |None -> T
                |Some edge -> edge::T
            let D',connect' =
                rest
                |>Seq.fold (fun (d,c) (w,v)-> 
                    match W u v with
                    |Some w when w < (d:Map<_,_>).[v] -> (Map.add v w d), (Map.add v (u,v) connect)
                    |_ -> d,c
                    ) (D,connect)

            let Q' = rest |> Seq.map (fun (_,v) -> D'.[v],v)|>Seq.sort|> Seq.toList
            loop D' connect' Q' T'
    let D = seq {yield s,0; yield! am.vertices|>Seq.filter ((<>) s)|>Seq.map (fun v -> v,System.Int32.MaxValue)}|> Map.ofSeq
    let T = []
    let Q = am.vertices |> Seq.map (fun v -> D.[v],v)|>Seq.sort|>Seq.toList
    loop D Map.empty Q T 
    |>List.rev

//let KruskalPrim (am:AdjacencyMatrix) (s:string)=
//    let t = [Vertex s ]|>Set.ofList 
//    let vertexIndices = am.vertices |> Seq.mapi (fun i x -> x,i)|>Map.ofSeq
//    let enqueueEdges start pQ=
//        let row=vertexIndices.[start]
//        let vertexWeights= weights.[row]
//        vertexWeights 
//        |> Seq.mapi (fun c w -> 
//            let endpoint = am.vertices.[c]
//            WEdge(w,start,endpoint))
//        |> Seq.filter (function |WEdge (weight=weight) -> (weight>=0))
//        |> Seq.fold (flip enqueue) pQ
//    let pQ= enqueueEdges s []
//    let rec loop pQ (t : _ Set) =
//        match dequeue pQ with
//        | None,_ -> t 
//        | Some edge, pQ ->  
//            match edge with
//            |WEdge(_,_,ev) when not(t.Contains(Vertex ev)) ->
//                let t' = t |>Set.add (Vertex ev)|> Set.add (Edge edge)
//                let pQ'=enqueueEdges ev pQ
//                loop pQ' t'
//            |_ -> loop pQ t 
//    loop pQ t
    

//let am = {vertices = header; weights = weights}
//let s = "C"

//Prim am s|>Seq.filter (function | Edge _ -> true | _ -> false)|>Seq.toArray



let adjMatrix=
    """
A  B  C  D  E  F
-  1  -  -  -  -
-  5  -  1  11 -
-  -  -  -  -  -
5  -  -  -  3  -
-  -  -  11 -  7
-  -  3  -  -  -  
    """|> buildAdjMatrix
PrimJarnik adjMatrix "A"
//A  B  C  D  E  F
//-  1  -  5  -  -
//1  5  -  1  11 -
//-  -  -  -  -  3
//5  1  -  -  11 -
//-  11 -  11 -  7
//-  -  3  -  7  -

//A  B  C  D  E  F
//-  1  -  -  -  -
//-  5  -  1  11 -
//-  -  -  -  -  -
//5  -  -  -  3  -
//-  -  -  11 -  7
//-  -  3  -  -  -


