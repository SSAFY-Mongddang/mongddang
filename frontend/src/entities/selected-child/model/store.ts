import { ConnectedUser } from '@/shared/api/user'
import { create } from 'zustand'

interface SelectedChildState {
  selectedChild: ConnectedUser | null
  setSelectedChild: (child: ConnectedUser) => void
  clearSelectedChild: () => void
}

export const useSelectedChildStore = create<SelectedChildState>((set) => ({
  selectedChild: null,
  setSelectedChild: (child) => set({ selectedChild: child }),
  clearSelectedChild: () => set({ selectedChild: null })
}))