package expected

import java.lang.IllegalArgumentException
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import pm.gnosis.model.Solidity
import pm.gnosis.model.SolidityBase

class Abi13 {
    object Events {
        object Submission {
            const val EVENT_ID: String = "0c5212e9d002fa3e0c9bd8c78b6d4df3e94f4e956761bd40f0859c979600a2e7"

            fun decode(topics: List<String>): Arguments {
                // Decode topics
                val topicsSource = SolidityBase.PartitionData(topics)
                if (topicsSource.consume() != EVENT_ID) throw IllegalArgumentException("topics[0] does not match event id")
                val t1 = topicsSource.consume()
                val t2 = topicsSource.consume()
                val t3 = topicsSource.consume()
                return Arguments(t1, t2, t3)
            }

            data class Arguments(
                    val bytesHash: String,
                    val stringHash: String,
                    val tupleHash: String
            )
        }
    }

    data class TupleA(val x: Solidity.UInt256, val y: Solidity.UInt256) : SolidityBase.StaticType {
        override fun encode(): String = SolidityBase.encodeFunctionArguments(x, y)

        class Decoder : SolidityBase.TypeDecoder<TupleA> {
            override fun isDynamic(): Boolean = false
            override fun decode(source: SolidityBase.PartitionData): TupleA {
                val arg0 = Solidity.UInt256.DECODER.decode(source)
                val arg1 = Solidity.UInt256.DECODER.decode(source)
                return TupleA(arg0, arg1)
            }
        }
        companion object {
            val DECODER: Decoder = Decoder()
        }
    }
}
